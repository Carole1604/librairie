<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Recherche Avancée</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Recherche Avancée</h2>
    <form method="get" action="/recherche" class="mb-4">
        <div class="row">
            <div class="col-md-3 mb-3">
                <label for="titre" class="form-label">Titre</label>
                <input type="text" class="form-control" id="titre" name="titre" value="${titre}">
            </div>
            <div class="col-md-3 mb-3">
                <label for="auteur" class="form-label">Auteur</label>
                <input type="text" class="form-control" id="auteur" name="auteur" value="${auteur}">
            </div>
            <div class="col-md-3 mb-3">
                <label for="langue" class="form-label">Langue</label>
                <input type="text" class="form-control" id="langue" name="langue" value="${langue}">
            </div>
            <div class="col-md-3 mb-3">
                <label for="categorie" class="form-label">Catégorie</label>
                <input type="text" class="form-control" id="categorie" name="categorie" value="${categorie}">
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Rechercher</button>
    </form>
    <c:if test="${not empty resultats}">
        <h4>Résultats</h4>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th>
                <th>Titre</th>
                <th>Auteur</th>
                <th>Langue</th>
                <th>Catégorie</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="livre" items="${resultats}">
                <tr>
                    <td>${livre.id}</td>
                    <td>${livre.titre}</td>
                    <td>${livre.auteur}</td>
                    <td>
                        <c:forEach var="desc" items="${livre.descriptions}">
                            ${desc.langue}
                        </c:forEach>
                    </td>
                    <td>
                        <c:forEach var="desc" items="${livre.descriptions}">
                            ${desc.categorie}
                        </c:forEach>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${empty resultats}">
        <div class="alert alert-info">Aucun résultat pour cette recherche.</div>
    </c:if>
</div>
</body>
</html> 