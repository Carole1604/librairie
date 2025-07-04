<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Livres</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Liste des Livres</h2>
    <a href="ajout" class="btn btn-primary mb-3">Ajouter un livre</a>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>ID</th>
            <th>Titre</th>
            <th>Auteur</th>
            <th>ISBN</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="livre" items="${livres}">
            <tr>
                <td>${livre.id}</td>
                <td>${livre.titre}</td>
                <td>${livre.auteur}</td>
                <td>${livre.isbn}</td>
                <td>
                    <a href="modifier/${livre.id}" class="btn btn-warning btn-sm">Modifier</a>
                    <a href="supprimer/${livre.id}" class="btn btn-danger btn-sm">Supprimer</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html> 