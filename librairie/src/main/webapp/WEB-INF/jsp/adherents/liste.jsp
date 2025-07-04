<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Adhérents</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Liste des Adhérents</h2>
    <a href="/adherents/inscription" class="btn btn-primary mb-3">Nouvel adhérent</a>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Login</th>
            <th>Email</th>
            <th>Type</th>
            <th>Actif</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="adherent" items="${adherents}">
            <tr>
                <td>${adherent.id}</td>
                <td>${adherent.nom}</td>
                <td>${adherent.prenom}</td>
                <td>${adherent.login}</td>
                <td>${adherent.email}</td>
                <td>
                    <c:choose>
                        <c:when test="${adherent.estEtudiant}">Étudiant</c:when>
                        <c:when test="${adherent.estProfessionnel}">Professionnel</c:when>
                        <c:otherwise>Anonyme</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${adherent.estActif}">Oui</c:when>
                        <c:otherwise>Non</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <a href="/adherents/modifier/${adherent.id}" class="btn btn-warning btn-sm">Modifier</a>
                    <form action="/adherents/desactiver" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="${adherent.id}" />
                        <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Désactiver cet adhérent ?');">Désactiver</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html> 