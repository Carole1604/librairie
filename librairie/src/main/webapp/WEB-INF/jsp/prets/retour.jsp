<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Retour de Livre</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Retour de Livre</h2>
    <form action="/prets/retour" method="post">
        <input type="hidden" name="id" value="${pret.id}" />
        <div class="mb-3">
            <label class="form-label">Adhérent</label>
            <input type="text" class="form-control" value="${pret.adherent.nom} ${pret.adherent.prenom}" disabled>
        </div>
        <div class="mb-3">
            <label class="form-label">Exemplaire</label>
            <input type="text" class="form-control" value="${pret.exemplaire.nomExemplaire}" disabled>
        </div>
        <div class="mb-3">
            <label class="form-label">Date début</label>
            <input type="text" class="form-control" value="${pret.dateDebut}" disabled>
        </div>
        <div class="mb-3">
            <label class="form-label">Date fin prévue</label>
            <input type="text" class="form-control" value="${pret.dateFinPrevue}" disabled>
        </div>
        <div class="mb-3">
            <label for="dateRenduReelle" class="form-label">Date de retour réelle</label>
            <input type="date" class="form-control" id="dateRenduReelle" name="dateRenduReelle" required>
        </div>
        <button type="submit" class="btn btn-success">Enregistrer le retour</button>
        <a href="/prets/liste" class="btn btn-secondary">Annuler</a>
    </form>
</div>
</body>
</html> 