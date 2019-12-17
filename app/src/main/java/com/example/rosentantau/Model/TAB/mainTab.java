package com.example.rosentantau.Model.TAB;

public class mainTab {
    private Long idReg;
    private String fecha;
    private String codebar;
    private String selSpinner;
    private String terminal;

    public mainTab() {
    }

    public mainTab(Long idReg, String fecha, String codebar, String selSpinner, String terminal) {
        this.idReg = idReg;
        this.fecha = fecha;
        this.codebar = codebar;
        this.selSpinner = selSpinner;
        this.terminal = terminal;
    }

    public Long getIdReg() {
        return idReg;
    }

    public void setIdReg(long idReg) {
        this.idReg = idReg;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCodebar() {
        return codebar;
    }

    public void setCodebar(String codebar) {
        this.codebar = codebar;
    }

    public String getSelSpinner() {
        return selSpinner;
    }

    public void setSelSpinner(String selSpinner) {
        this.selSpinner = selSpinner;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    @Override
    public String toString(){
        return "{" +
                "\"idReg\":"+idReg+",\n"+
                "\"fecha\":\""+fecha+"\",\n"+
                "\"codebar\":\""+codebar+"\",\n"+
                "\"selSpinner\":\""+selSpinner+"\",\n"+
                "\"terminal\":\""+terminal+"\"\n"+
                "} \n";
    }
}
