package com.mycompany.javagendatelefonicav2.controllers;

import com.mycompany.javagendatelefonicav2.dao.ContatoDAO;
import com.mycompany.javagendatelefonicav2.pojos.Contato;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(name = "ContatoController", urlPatterns = {"/ContatoController"})
@MultipartConfig(maxFileSize=1024*1024*16,
                maxRequestSize=1024*1024*16)
public class ContatoController extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        String query = request.getParameter("query");
        
        if(query.equals("list")) {            
            String indice = request.getParameter("indice");
            if(indice == null || indice.equals("")) {
                indice = "1";
            }
            RequestDispatcher rd = request.getRequestDispatcher("list.jsp");
            ContatoDAO dao = new ContatoDAO();            
            request.setAttribute("paginas", dao.getNumberOfPages());
            request.setAttribute("indice", indice);
            request.setAttribute("contatos", dao.list(Integer.valueOf(indice)));            
            rd.forward(request, response);
        }
        else if(query.equals("new")) {
            RequestDispatcher rd = request.getRequestDispatcher("novo.jsp");            
            rd.forward(request, response);
        }
        else if(query.equals("salvar")) {
            Contato contato = new Contato();
            contato.setNome(request.getParameter("nome"));
            contato.setTelefone(request.getParameter("telefone"));
            contato.setEmail(request.getParameter("email"));
            contato.setAnotacoes(request.getParameter("anotacoes"));
            ContatoDAO dao = new ContatoDAO();
            int resultado = dao.save(contato);
            if(resultado == 1) {
                RequestDispatcher rd = request.getRequestDispatcher("index.jsp");                
                rd.forward(request, response);
            }
            else {
                RequestDispatcher rd = request.getRequestDispatcher("novo.jsp");
                rd.forward(request, response);
            }
        }
        else if(query.equals("edita")) {
            Integer id = Integer.valueOf(request.getParameter("id"));
            ContatoDAO dao = new ContatoDAO();
            RequestDispatcher rd = request.getRequestDispatcher("edita.jsp");
            request.setAttribute("contato", dao.read(id));            
            rd.forward(request, response);
        }
        else if(query.equals("editar")) {         
            Contato contato = new Contato();        
            contato.setId(Integer.valueOf(request.getParameter("id")));
            contato.setNome(request.getParameter("nome"));
            contato.setTelefone(request.getParameter("telefone"));
            contato.setEmail(request.getParameter("email"));
            contato.setAnotacoes(request.getParameter("anotacoes"));
            
            Part part = request.getPart("foto");
            if(part.getSize() == 0) {
                contato.setFoto(null);
            }
            String type = part.getContentType();
            if(part.getSize() != 0 && !type.equals("image/jpeg") && !type.equals("image/jpg") && !type.equals("image/png")) {
                RequestDispatcher rd = request.getRequestDispatcher("/edita.jsp");
                request.setAttribute("error", "Os arquivos devem ser imagens do tipo JPEG, PNG ou GIF");
                ContatoDAO dao = new ContatoDAO();
                request.setAttribute("contato", dao.read(contato.getId()));                
                rd.forward(request, response);
            }
            else if(part.getSize() > 16000000) {
                RequestDispatcher rd = request.getRequestDispatcher("/edita.jsp");
                request.setAttribute("error", "A imagem não deve ser maior que 16MB");
                ContatoDAO dao = new ContatoDAO();
                request.setAttribute("contato", dao.read(contato.getId()));                
                rd.forward(request, response);
            }
            else {                
                InputStream is = part.getInputStream();                
                ContatoDAO dao = new ContatoDAO();
                int resultado = dao.edit(contato, part.getSize() == 0 ? null : is);
                if(resultado == 1) {
                    RequestDispatcher rd = request.getRequestDispatcher("index.jsp");                                           
                    rd.forward(request, response);
                }
                else {                        
                    RequestDispatcher rd = request.getRequestDispatcher("edita.jsp");
                    request.setAttribute("error", "Erro ao tentar editar contato");
                    request.setAttribute("contato", dao.read(contato.getId()));                        
                    rd.forward(request, response);
                }
            }                   
        }
        else if(query.equals("read")) {
            Integer id = Integer.valueOf(request.getParameter("id"));
            ContatoDAO dao = new ContatoDAO();
            RequestDispatcher rd = request.getRequestDispatcher("visualiza.jsp");
            request.setAttribute("contato", dao.read(id));            
            rd.forward(request, response);
        }
        else if(query.equals("delete")) {
            ContatoDAO dao = new ContatoDAO();                        
            dao.delete(Integer.valueOf(request.getParameter("id")));
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
        }
        else {
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");                   
            rd.forward(request, response);
        }
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}