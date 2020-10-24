package learn.field_agent.domain;

import learn.field_agent.data.AgencyAgentRepository;
import learn.field_agent.data.SecurityClearanceRepository;
import learn.field_agent.models.AgencyAgent;
import learn.field_agent.models.SecurityClearance;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityClearanceService {

    private final SecurityClearanceRepository securityClearanceRepository;
    // To determine if securityClearance is being used anywhere before deletion
    private final AgencyAgentRepository agencyAgentRepository;

    public SecurityClearanceService (SecurityClearanceRepository securityClearanceRepository, AgencyAgentRepository agencyAgentRepository) {
        this.securityClearanceRepository = securityClearanceRepository;
        this.agencyAgentRepository = agencyAgentRepository;
    }

    public List<SecurityClearance> findAll() { return securityClearanceRepository.findAll(); }

    public SecurityClearance findById(int securityClearanceId) { return securityClearanceRepository.findById(securityClearanceId); }

    public Result<SecurityClearance> add (SecurityClearance securityClearance) {

        Result<SecurityClearance> result = validate(securityClearance);

        if(!result.isSuccess()) {
            return result;
        }

        if (securityClearance.getSecurityClearanceId() != 0) {
            result.addMessage("securityClearanceId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        securityClearance = securityClearanceRepository.add(securityClearance);
        result.setPayload(securityClearance);
        return result;
    }

    public Result<SecurityClearance> update(SecurityClearance securityClearance) {

        Result<SecurityClearance> result = validate(securityClearance);

        if(!result.isSuccess()) {
            return result;
        }

        if (securityClearance.getSecurityClearanceId() != 0) {
            result.addMessage("securityClearanceId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if(!securityClearanceRepository.update(securityClearance)) {
            String msg = String.format("securityClearanceId: %s, not found", securityClearance.getSecurityClearanceId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public Result<SecurityClearance> deleteById(int securityClearanceId) {


        //List<AgencyAgent> allAgencyAgent = agencyAgentRepository.findAll();

        return null;
    }

    private Result<SecurityClearance> validate(SecurityClearance securityClearance) {

        Result<SecurityClearance> result = new Result<>();

        if(securityClearance == null) {
            result.addMessage("SecurityClearance cannot be null", ResultType.INVALID);
            return result;
        }

        for(SecurityClearance iteratedClearance : findAll()) {
            if(securityClearance.getName() == iteratedClearance.getName()
            && securityClearance != iteratedClearance) {
                result.addMessage("SecurityClearance name must be unique", ResultType.INVALID);
                return result;
            }
        }

        if(Validations.isNullOrBlank(securityClearance.getName())) {
            result.addMessage("Security Clearance name is required", ResultType.INVALID);
        }

        return result;
    }

}