<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Supprimer un Exemplaire</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Supprimer un Exemplaire du livre : ${livre.titre}</h2>
    <form action="/exemplaires/supprimer" method="post">
        <input type="hidden" name="id" value="${exemplaire.id}" />
        <input type="hidden" name="livreId" value="${livre.id}" />
        <p>Êtes-vous sûr de vouloir supprimer l'exemplaire suivant ?</p>
        <ul>
            <li><strong>Nom :</strong> ${exemplaire.nomExemplaire}</li>
            <li><strong>État :</strong> ${exemplaire.etat}</li>
            <li><strong>Code-barre :</strong> ${exemplaire.codeBarre}</li>
        </ul>
        <button type="submit" class="btn btn-danger">Supprimer</button>
        <a href="/exemplaires/liste/${livre.id}" class="btn btn-secondary">Annuler</a>
    </form>
</div>
</body>
</html> 