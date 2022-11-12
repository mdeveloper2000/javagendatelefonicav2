<%@page contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css" />        
        <title>JSP Edita</title>
    </head>
    
    <body>
        
        <div class="container">
            <jsp:include page="navbar.jsp"></jsp:include>
            <div class="row">
                <div class="col s6 center">
                    <i class="large material-icons" style="font-size: 256px;">
                        <c:choose>
                            <c:when test="${contato.foto == null}">
                                <i class="material-icons" style="font-size: 256px;">not_interested</i>
                            </c:when>
                            <c:otherwise>
                                <img src="data:image/jpg;base64,<c:out value="${contato.foto}" />" width="450" height="500" />
                            </c:otherwise>
                        </c:choose>                        
                    </i>
                    <h6 class="center" style="font-weight: bold; margin-top: 0;">Foto de <c:out value="${contato.nome}" /></h6>
                </div>                
                    <form method="post" action="ContatoController?query=editar" enctype="multipart/form-data">  
                    <div class="col s6">
                        <input type="hidden" id="id" name="id" value="${requestScope.contato.id}" />
                        <div class="input-field col s12">
                            <i class="material-icons prefix">account_circle</i>
                            <input name="nome" id="nome" type="text" class="validate" required maxlength="30" value="${requestScope.contato.nome}" onblur="this.value=this.value.trim().toUpperCase();">
                            <label for="nome">Nome</label>
                        </div>
                        <div class="input-field col s12">
                            <i class="material-icons prefix">local_phone</i>
                            <input name="telefone" id="telefone" type="text" class="validate" required maxlength="14" value="${requestScope.contato.telefone}">
                            <label for="telefone">Telefone</label>
                        </div>
                        <div class="input-field col s12">
                            <i class="material-icons prefix">email</i>
                            <input name="email" id="email" type="email" class="validate" required maxlength="40" value="${requestScope.contato.email}" onblur="this.value=this.value.trim().toLowerCase();">
                            <label for="email">E-mail</label>
                        </div>
                        <div class="input-field col s12">
                            <div class="file-field input-field">
                                <div class="btn black">
                                    <i class="material-icons">photo_camera</i>
                                    <input type="file" name="foto" id="foto" />                                    
                                </div>
                                <div class="file-path-wrapper">
                                    <input class="file-path validate" type="text" placeholder="Foto" />
                                </div>                                
                            </div>
                        </div>
                        <div class="input-field col s12">
                            <i class="material-icons prefix">event_note</i>
                            <textarea name="anotacoes" id="anotacoes" class="materialize-textarea" data-length="150" maxlength="150" 
                                      style="min-height: 80px; max-height: 80px;"><c:out value="${requestScope.contato.anotacoes}" /></textarea>
                            <label for="anotacoes">Anotações</label>
                        </div>    
                        <div class="input-field col s12">
                            <button class="btn waves-effect waves-light blue right" type="submit">Editar
                                <i class="material-icons right">edit</i>
                            </button>
                        </div>
                        <div class="input-field col s12">
                            <c:if test="${requestScope.error != null}">
                                <c:out value="${requestScope.error}" />
                            </c:if>
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