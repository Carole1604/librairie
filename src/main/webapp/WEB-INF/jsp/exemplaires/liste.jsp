<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Exemplaires</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Exemplaires du livre : ${livre.titre}</h2>
    <a href="/exemplaires/ajout/${livre.id}" class="btn btn-primary mb-3">Ajouter un exemplaire</a>
    <a href="/livres/liste" class="btn btn-secondary mb-3">Retour à la liste des livres</a>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>État</th>
            <th>Date acquisition</th>
            <th>Localisation</th>
            <th>Code-barre</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="exemplaire" items="${exemplaires}">
            <tr>
                <td>${exemplaire.id}</td>
                <td>${exemplaire.nomExemplaire}</td>
                <td>${exemplaire.etat}</td>
                <td>${exemplaire.dateAcquisition}</td>
                <td>${exemplaire.localisation}</td>
                <td>${exemplaire.codeBarre}</td>
                <td>
                    <a href="/exemplaires/modifier/${exemplaire.id}" class="btn btn-warning btn-sm">Modifier</a>
                    <a href="/exemplaires/supprimer/${exemplaire.id}" class="btn btn-danger btn-sm">Supprimer</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html> 