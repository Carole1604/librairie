<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Statistiques - Tableau de Bord</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Statistiques de la Bibliothèque</h2>
    <form method="get" action="/tableau_de_bord/statistiques" class="mb-4">
        <label for="dateStat" class="form-label">Date :</label>
        <input type="date" id="dateStat" name="date" value="${dateStat}" class="form-control" style="max-width:200px;display:inline-block;">
        <button type="submit" class="btn btn-primary">Afficher</button>
    </form>
    <c:choose>
        <c:when test="${not empty stats}">
            <table class="table table-bordered">
                <tr><th>Nombre de prêts</th><td>${stats.nombrePrets}</td></tr>
                <tr><th>Nombre de retours</th><td>${stats.nombreRetours}</td></tr>
                <tr><th>Nombre de réservations</th><td>${stats.nombreReservations}</td></tr>
                <tr><th>Nombre de pénalités</th><td>${stats.nombrePenalites}</td></tr>
                <tr><th>Nombre d'adhérents actifs</th><td>${stats.nombreAdherentsActifs}</td></tr>
                <tr><th>Nombre de livres disponibles</th><td>${stats.nombreLivresDisponibles}</td></tr>
            </table>
        </c:when>
        <c:otherwise>
            <div class="alert alert-warning">Aucune statistique disponible pour cette date.</div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html> 