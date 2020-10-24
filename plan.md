## Field Agent

### Security Clearance
- [x] Repository
    - [x] findAll
        - [x] test
    - [x] add
        - [x] test
    - [x] update
        - [x] test
    - [x] delete
        - [x] test
- [ ] Domain
    - [x] findAll
        - [x] test
    - [ ] add
        - [ ] test
    - [ ] update
        - [ ] Need to update all instances in agency_agent
        - [ ] test
    - [ ] delete
        - Ensure security clearance isn't references anymore to allow deletion (used in agency_agent table)
    - [ ] test
- [ ] Controller
    - [ ] findAll
        - [ ] test
    - [ ] add
        - [ ] test
    - [ ] update
        - [ ] test
    - [ ] delete
        - [ ] test

### Aliases
- [ ] Repository
    - [ ] findAll (from agent perspective)
        - [ ] test       
    - [ ] add
        - [ ] test
    - [ ] update
        - [ ] test
    - [ ] delete
        - [ ] test
- [ ] Domain
    - [ ] findAll
        - [ ] test
    - [ ] findById
        - [ ] test         
    - [ ] add
        - [ ] test
    - [ ] update
        - [ ] test
    - [ ] delete
        - 
    - [ ] test
- [ ] Controller
    - [ ] findAll
        - [ ] test
    - [ ] findById
        - [ ] test         
    - [ ] add
        - [ ] test
    - [ ] update
        - [ ] test
    - [ ] delete
        - [ ] test

### Global Error Handling
- [ ] Create GlobalExceptionHandler class
- [ ] Create specific exception handlers within
    - [ ] DataAccessException (more specific?)
    - [ ] IllegalArgumentException
    - [ ] Exception (last resort)
    - [ ] try to find any others that may occur
    
### Stretch
- [ ] CRUD - Mission
    - M:1 Relationship with agency, just need agency_id in mission
    - M:M Relationship with agent, will contain List<Agent> to avoid changing agent model and related CRUD
- [ ] CRUD - Mission/Agent
- [ ]