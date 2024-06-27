package com.ws.cvlan.repository;

import com.ws.cvlan.enums.CheckCvlanBlockExistsAttr;
import com.ws.cvlan.enums.CvlanExistStructureAttr;
import com.ws.cvlan.enums.OperationResult;
import com.ws.cvlan.enums.Status;
import com.ws.cvlan.filter.AddCvlanBlockFilter;
import com.ws.cvlan.pojo.AddCvlanBlock;
import com.ws.cvlan.pojo.DTOs.CheckCvlanBlockExistsDTO;
import com.ws.cvlan.sql.CheckCvlanBlockExists;
import com.ws.cvlan.sql.CvlanSql;
import com.ws.cvlan.sql.FindServiceByCvlanSql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

import static com.ws.cvlan.util.StringUtilSol.getLong;
import static com.ws.cvlan.util.StringUtilSol.getString;

//TODO: Ficou faltando retornar informações de auditoria quando o bloqueio ja existe e retornar o ID quando é criado
//TODO: Trocar console.log por LOGs
@Repository
public class CvlanRepository {

    private final MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public CvlanRepository() {
    }

    public boolean serviceExistByCvlan(AddCvlanBlockFilter addCvlanBlockFilter) {
        String query = FindServiceByCvlanSql.getQueryFindServiceByCvlan(addCvlanBlockFilter, sqlParameterSource);
        List<Map<String, Object>> resultTuples = jdbcTemplate.queryForList(query, sqlParameterSource);
        return !resultTuples.isEmpty() && !getString(resultTuples.get(0), CvlanExistStructureAttr.NAME).isEmpty();
    }

    public boolean checkCvlanBlockExists(AddCvlanBlockFilter addCvlanBlockFilter) {
        CheckCvlanBlockExistsDTO checkCvlanBlockExistsDTO = new CheckCvlanBlockExistsDTO(
                addCvlanBlockFilter.getSvlan(),
                addCvlanBlockFilter.getCvlan()
        );

        String query = CheckCvlanBlockExists.getQueryCheckCvlanBlockExists(checkCvlanBlockExistsDTO, sqlParameterSource);
        List<Map<String, Object>> resultTuples = jdbcTemplate.queryForList(query, sqlParameterSource);
        Long status = resultTuples.isEmpty() ? 0L : getLong(resultTuples.get(0), CheckCvlanBlockExistsAttr.STATUS);
        return !status.equals(0L);
    }

    @Transactional
    public AddCvlanBlock addCvlanBlock(AddCvlanBlockFilter addCvlanBlockFilter) {

        System.out.println("Iniciando verificação da existência do serviço para o filtro fornecido...");
        boolean serviceExists = serviceExistByCvlan(addCvlanBlockFilter);
        System.out.println("Serviço existente: " + serviceExists);

        if (!serviceExists) {
            System.out.println("Serviço não encontrado para o filtro fornecido. Retornando erro.");
            return createResponse(OperationResult.CVLAN_NOT_FOUND);
        }

        System.out.println("Iniciando verificação se a CVLAN já está bloqueada ...");
        boolean cvlanAlreadyBlocked = checkCvlanBlockExists(addCvlanBlockFilter);
        System.out.println("Verificação de bloqueio da CVLAN: " + cvlanAlreadyBlocked);
        if (checkCvlanBlockExists(addCvlanBlockFilter)) {
            System.out.println("A CVLAN já está bloqueada.");
            return createResponse(OperationResult.CVLAN_BLOCKED);
        }

        String query = CvlanSql.getQueryAddCvlanBlock(addCvlanBlockFilter, sqlParameterSource);
        System.out.println("Query gerada para adicionar o bloqueio de CVLAN:");
        System.out.println(query);
        System.out.println("Parâmetros da query: " + sqlParameterSource.getValues());

        try {
            System.out.println("Executando a query para bloquear o CVLAN...");
            jdbcTemplate.update(query, sqlParameterSource);
            System.out.println("CVLAN bloqueada com sucesso.");

            return createResponse(OperationResult.SUCCESS);
        } catch (Exception e) {
            System.out.println("Erro ao bloquear o CVLAN:");
            System.out.println("Mensagem de erro: " + e.getMessage());
            e.printStackTrace();
            return createResponse(OperationResult.UNKNOWN_ERROR);
        }
    }

    private AddCvlanBlock createResponse(OperationResult operationResult) {
        AddCvlanBlock cvlanBlock = new AddCvlanBlock();
        cvlanBlock.setOperationResult(operationResult);

        if (Status.SUCCESS.equals(operationResult.getStatus())) {
            cvlanBlock.setId(1L);
        }

        return cvlanBlock;
    }
}
