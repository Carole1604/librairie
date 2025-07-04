<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Modifier la Description</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Modifier la Description du livre : ${livre.titre}</h2>
    <form action="/descriptions/modifier" method="post">
        <input type="hidden" name="id" value="${description.id}" />
        <input type="hidden" name="livre.id" value="${livre.id}" />
        <div class="mb-3">
            <label for="resume" class="form-label">Résumé</label>
            <textarea class="form-control" id="resume" name="resume">${description.resume}</textarea>
        </div>
        <div class="mb-3">
            <label for="langue" class="form-label">Langue</label>
            <input type="text" class="form-control" id="langue" name="langue" value="${description.langue}">
        </div>
        <div class="mb-3">
            <label for="nombrePages" class="form-label">Nombre de pages</label>
            <input type="number" class="form-control" id="nombrePages" name="nombrePages" min="1" value="${description.nombrePages}">
        </div>
        <div class="mb-3">
            <label for="categorie" class="form-label">Catégorie</label>
            <input type="text" class="form-control" id="categorie" name="categorie" value="${description.categorie}">
        </div>
        <div class="mb-3">
            <label for="editeur" class="form-label">Éditeur</label>
            <input type="text" class="form-control" id="editeur" name="editeur" value="${description.editeur}">
        </div>
        <button type="submit" class="btn btn-success">Enregistrer</button>
        <a href="/livres/liste" class="btn btn-secondary">Annuler</a>
    </form>
</div>
</body>
</html> 