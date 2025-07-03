<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Retours</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Liste des Retours de Livres</h2>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>ID Prêt</th>
            <th>Adhérent</th>
            <th>Exemplaire</th>
            <th>Date début</th>
            <th>Date fin prévue</th>
            <th>Date rendu réelle</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="pret" items="${prets}">
            <c:if test="${pret.dateRenduReelle != null}">
                <tr>
                    <td>${pret.id}</td>
                    <td>${pret.adherent.nom} ${pret.adherent.prenom}</td>
                    <td>${pret.exemplaire.nomExemplaire}</td>
                    <td>${pret.dateDebut}</td>
                    <td>${pret.dateFinPrevue}</td>
                    <td>${pret.dateRenduReelle}</td>
                </tr>
            </c:if>
        </c:forEach>
        </tbody>
    </table>
    <a href="/prets/liste" class="btn btn-secondary">Retour à la liste des prêts</a>
</div>
</body>
</html> 