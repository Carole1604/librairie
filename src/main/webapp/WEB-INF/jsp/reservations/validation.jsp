<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Validation Réservation</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Validation d'une Réservation</h2>
    <form action="/reservations/validation" method="post">
        <input type="hidden" name="id" value="${reservation.id}" />
        <div class="mb-3">
            <label class="form-label">Adhérent</label>
            <input type="text" class="form-control" value="${reservation.adherent.nom} ${reservation.adherent.prenom}" disabled>
        </div>
        <div class="mb-3">
            <label class="form-label">Livre</label>
            <input type="text" class="form-control" value="${reservation.livre.titre}" disabled>
        </div>
        <div class="mb-3">
            <label for="statut" class="form-label">Statut</label>
            <select class="form-control" id="statut" name="statut.id">
                <option value="2">Validée</option>
                <option value="3">Refusée</option>
            </select>
        </div>
        <button type="submit" class="btn btn-success">Valider</button>
        <a href="/reservations/liste" class="btn btn-secondary">Annuler</a>
    </form>
</div>
</body>
</html> 