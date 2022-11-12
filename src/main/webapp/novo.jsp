<%@page contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css" />        
        <title>JSP Novo</title>
    </head>
    
    <body>
        
        <div class="container">
            <jsp:include page="navbar.jsp"></jsp:include>
            <div class="row">
                <div class="col s6 center">
                    <i class="large material-icons" style="font-size: 256px;">
                        contact_phone
                    </i>
                    <h5 class="center" style="font-weight: bold; margin-top: 0;">Adicionar Contato</h5>
                </div>                
                <form method="post" action="ContatoController?query=salvar">  
                    <div class="col s6">
                        <div class="input-field col s12">
                            <i class="material-icons prefix">account_circle</i>
                            <input name="nome" id="nome" type="text" class="validate" required maxlength="30" onblur="this.value=this.value.trim().toUpperCase();">
                            <label for="nome">Nome</label>
                        </div>
                        <div class="input-field col s12">
                            <i class="material-icons prefix">local_phone</i>
                            <input name="telefone" id="telefone" type="text" class="validate" required maxlength="14">
                            <label for="telefone">Telefone</label>
                        </div>
                        <div class="input-field col s12">
                            <i class="material-icons prefix">email</i>
                            <input name="email" id="email" type="email" class="validate" required maxlength="40" onblur="this.value=this.value.trim().toLowerCase();">
                            <label for="email">E-mail</label>
                        </div>
                        <div class="input-field col s12">
                            <i class="material-icons prefix">event_note</i>
                            <textarea id="anotacoes" class="materialize-textarea" data-length="150" maxlength="150" 
                                      style="min-height: 100px; max-height: 100px;"></textarea>
                            <label for="anotacoes">Anotações</label>
                        </div>
                        <div class="input-field col s12">
                            <button class="btn waves-effect waves-light right" type="submit">Salvar
                                <i class="material-icons right">save</i>
                            </button>
                        </div>
                    </div>
                </form>                              
            </div>
        </div>
            
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                var textNeedCount = document.querySelectorAll('#anotacoes');
                M.CharacterCounter.init(textNeedCount);
            });
        </script>    
    
    </body>
    
</html>