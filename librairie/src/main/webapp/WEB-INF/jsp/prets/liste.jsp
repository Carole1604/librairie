<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Prêts</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Liste des Prêts en cours</h2>
    <a href="/prets/creation" class="btn btn-primary mb-3">Nouveau prêt</a>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>ID</th>
            <th>Adhérent</th>
            <th>Exemplaire</th>
            <th>Date début</th>
            <th>Date fin prévue</th>
            <th>Date rendu réelle</th>
            <th>Type</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="pret" items="${prets}">
            <tr>
                <td>${pret.id}</td>
                <td>${pret.adherent.nom} ${pret.adherent.prenom}</td>
                <td>${pret.exemplaire.nomExemplaire}</td>
                <td>${pret.dateDebut}</td>
                <td>${pret.dateFinPrevue}</td>
                <td>${pret.dateRenduReelle}</td>
                <td>${pret.typePret}</td>
                <td>
                    <a href="/prets/retour/${pret.id}" class="btn btn-success btn-sm">Retour</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html> 