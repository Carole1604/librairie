<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Réservations</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Liste des Réservations</h2>
    <a href="/reservations/creation" class="btn btn-primary mb-3">Nouvelle réservation</a>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>ID</th>
            <th>Adhérent</th>
            <th>Livre</th>
            <th>Date réservation</th>
            <th>Statut</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="reservation" items="${reservations}">
            <tr>
                <td>${reservation.id}</td>
                <td>${reservation.adherent.nom} ${reservation.adherent.prenom}</td>
                <td>${reservation.livre.titre}</td>
                <td>${reservation.dateReservation}</td>
                <td>${reservation.statut.nomStatut}</td>
                <td>
                    <a href="/reservations/validation/${reservation.id}" class="btn btn-success btn-sm">Valider</a>
                    <a href="/reservations/annulation/${reservation.id}" class="btn btn-danger btn-sm">Annuler</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html> 