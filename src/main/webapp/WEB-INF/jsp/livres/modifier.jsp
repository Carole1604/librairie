<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Modifier un Livre</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Modifier un Livre</h2>
    <form action="/livres/modifier" method="post">
        <input type="hidden" name="id" value="${livre.id}" />
        <div class="mb-3">
            <label for="titre" class="form-label">Titre</label>
            <input type="text" class="form-control" id="titre" name="titre" value="${livre.titre}" required>
        </div>
        <div class="mb-3">
            <label for="auteur" class="form-label">Auteur</label>
            <input type="text" class="form-control" id="auteur" name="auteur" value="${livre.auteur}" required>
        </div>
        <div class="mb-3">
            <label for="isbn" class="form-label">ISBN</label>
            <input type="number" class="form-control" id="isbn" name="isbn" value="${livre.isbn}">
        </div>
        <div class="mb-3">
            <label for="edition" class="form-label">Édition</label>
            <input type="text" class="form-control" id="edition" name="edition" value="${livre.edition}">
        </div>
        <div class="mb-3">
            <label for="anneePublication" class="form-label">Année de publication</label>
            <input type="number" class="form-control" id="anneePublication" name="anneePublication" value="${livre.anneePublication}">
        </div>
        <div class="mb-3">
            <label for="nombreExemplaires" class="form-label">Nombre d'exemplaires</label>
            <input type="number" class="form-control" id="nombreExemplaires" name="nombreExemplaires" min="0" value="${livre.nombreExemplaires}">
        </div>
        <div class="mb-3">
            <label for="ageMinimum" class="form-label">Âge minimum</label>
            <input type="number" class="form-control" id="ageMinimum" name="ageMinimum" min="0" value="${livre.ageMinimum}">
        </div>
        <button type="submit" class="btn btn-success">Enregistrer</button>
        <a href="/livres/liste" class="btn btn-secondary">Annuler</a>
    </form>
</div>
</body>
</html> 