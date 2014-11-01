/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste.jsf.controller;

import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import teste.model.Contato;
import teste.model.ContatoDao;

/**
 *
 * @author Klaus Boeing
 */
@ManagedBean
public class CadastroContatoBean {
    
    private Contato contato = new Contato();

    @ManagedProperty("#{param.id}")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @PostConstruct
    public void init(){
        if(id != null){
            try {
                this.contato = new ContatoDao().buscarPorId(id);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    
    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }
    
    public String grava(){
        if(contato.getNome() == null) {
            FacesMessage message = new FacesMessage("O nome deve ser definido.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        
        if(contato.getDataNascimento() == null){
            FacesMessage message = new FacesMessage("A data de nascimento deve ser definida.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

        if(contato.getNome() == null || contato.getDataNascimento() == null){
            return null;
        }
        
        try {
            if(contato.getId() == null){
                new ContatoDao().adiciona(contato);
            }else{
                new ContatoDao().edita(contato);
            }            
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        
        return "lista-contatos";
    }
}