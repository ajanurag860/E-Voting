/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.dto;

import java.io.InputStream;

/**
 *
 * @author hp
 */
public class UpdateCandidateDTO {
    
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public InputStream getSymbol() {
        return Symbol;
    }

    public void setSymbol(InputStream Symbol) {
        this.Symbol = Symbol;
    }
    private String city;
    private String party;
    private InputStream Symbol;
    private String candidateid;

    public String getCandidateid() {
        return candidateid;
    }

    public void setCandidateid(String candidateid) {
        this.candidateid = candidateid;
    }
    
}
