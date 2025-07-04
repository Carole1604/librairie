<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Supprimer un Livre</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Supprimer un Livre</h2>
    <form action="/livres/supprimer" method="post">
        <input type="hidden" name="id" value="${livre.id}" />
        <p>Êtes-vous sûr de vouloir supprimer le livre suivant ?</p>
        <ul>
            <li><strong>Titre :</strong> ${livre.titre}</li>
            <li><strong>Auteur :</strong> ${livre.auteur}</li>
            <li><strong>ISBN :</strong> ${livre.isbn}</li>
        </ul>
        <button type="submit" class="btn btn-danger">Supprimer</button>
        <a href="/livres/liste" class="btn btn-secondary">Annuler</a>
    </form>
</div>
</body>
</html> 