<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Modifier un Exemplaire</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Modifier un Exemplaire du livre : ${livre.titre}</h2>
    <form action="/exemplaires/modifier" method="post">
        <input type="hidden" name="id" value="${exemplaire.id}" />
        <input type="hidden" name="livre.id" value="${livre.id}" />
        <div class="mb-3">
            <label for="nomExemplaire" class="form-label">Nom de l'exemplaire</label>
            <input type="text" class="form-control" id="nomExemplaire" name="nomExemplaire" value="${exemplaire.nomExemplaire}" required>
        </div>
        <div class="mb-3">
            <label for="etat" class="form-label">État</label>
            <select class="form-control" id="etat" name="etat">
                <option value="disponible" ${exemplaire.etat == 'disponible' ? 'selected' : ''}>Disponible</option>
                <option value="emprunte" ${exemplaire.etat == 'emprunte' ? 'selected' : ''}>Emprunté</option>
                <option value="reserve" ${exemplaire.etat == 'reserve' ? 'selected' : ''}>Réservé</option>
                <option value="en_reparation" ${exemplaire.etat == 'en_reparation' ? 'selected' : ''}>En réparation</option>
                <option value="perdu" ${exemplaire.etat == 'perdu' ? 'selected' : ''}>Perdu</option>
                <option value="retire" ${exemplaire.etat == 'retire' ? 'selected' : ''}>Retiré</option>
            </select>
        </div>
        <div class="mb-3">
            <label for="dateAcquisition" class="form-label">Date d'acquisition</label>
            <input type="date" class="form-control" id="dateAcquisition" name="dateAcquisition" value="${exemplaire.dateAcquisition}">
        </div>
        <div class="mb-3">
            <label for="localisation" class="form-label">Localisation</label>
            <input type="text" class="form-control" id="localisation" name="localisation" value="${exemplaire.localisation}">
        </div>
        <div class="mb-3">
            <label for="codeBarre" class="form-label">Code-barre</label>
            <input type="text" class="form-control" id="codeBarre" name="codeBarre" value="${exemplaire.codeBarre}">
        </div>
        <button type="submit" class="btn btn-success">Enregistrer</button>
        <a href="/exemplaires/liste/${livre.id}" class="btn btn-secondary">Annuler</a>
    </form>
</div>
</body>
</html> 