package learn.field_agent.data.mappers;

import learn.field_agent.models.AgencyAgent;
import learn.field_agent.models.Agent;
import learn.field_agent.models.SecurityClearance;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AgencyAgentMapper implements RowMapper<AgencyAgent> {

    @Override
    public AgencyAgent mapRow(ResultSet resultSet, int i) throws SQLException {

        AgencyAgent agencyAgent = new AgencyAgent();
        agencyAgent.setAgencyId(resultSet.getInt("agency_id"));
        agencyAgent.setIdentifier(resultSet.getString("identifier"));
        agencyAgent.setActivationDate(resultSet.getDate("activation_date").toLocalDate());
        agencyAgent.setActive(resultSet.getBoolean("is_active"));

        // Had to use a workaround as SecurityClearanceMapper uses security_clearance_name as name which doesn't match table
        SecurityClearanceMapper securityClearanceMapper = new SecurityClearanceMapper();
        agencyAgent.setSecurityClearance(securityClearanceMapper.mapRow(resultSet, i));

//        SecurityClearanceMapper2 securityClearanceMapper2 = new SecurityClearanceMapper2();
//        agencyAgent.setSecurityClearance(securityClearanceMapper2.mapRow(resultSet, i));

        //agencyAgent.setSecurityClearance(new SecurityClearance(1, "Secret"));

        AgentMapper agentMapper = new AgentMapper();
        agencyAgent.setAgent(agentMapper.mapRow(resultSet, i));

        return agencyAgent;
    }
}
