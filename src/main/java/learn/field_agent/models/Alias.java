package learn.field_agent.models;

public class Alias {

    private int alias_id;
    private String name;
    private String persona;
    private int agent_id;

    public Alias() {

    }

    public Alias(int alias_id, String name, String persona, int agent_id) {
        this.alias_id = alias_id;
        this.name = name;
        this.persona = persona;
        this.agent_id = agent_id;
    }

    public int getAlias_id() {
        return alias_id;
    }

    public void setAlias_id(int alias_id) {
        this.alias_id = alias_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public int getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(int agent_id) {
        this.agent_id = agent_id;
    }
}
