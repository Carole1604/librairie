<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Pénalités</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Liste des Pénalités</h2>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>ID</th>
            <th>Adhérent</th>
            <th>Prêt</th>
            <th>Date début</th>
            <th>Date fin</th>
            <th>Montant</th>
            <th>Active</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="penalite" items="${penalites}">
            <tr>
                <td>${penalite.id}</td>
                <td>${penalite.adherent.nom} ${penalite.adherent.prenom}</td>
                <td>${penalite.pret.id}</td>
                <td>${penalite.dateDebut}</td>
                <td>${penalite.dateFin}</td>
                <td>${penalite.montant}</td>
                <td>
                    <c:choose>
                        <c:when test="${penalite.estActive}">Oui</c:when>
                        <c:otherwise>Non</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <a href="/penalites/details/${penalite.id}" class="btn btn-info btn-sm">Détails</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html> 