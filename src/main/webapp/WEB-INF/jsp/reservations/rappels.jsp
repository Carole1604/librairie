<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Rappels de Réservations</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Gestion des Rappels de Réservations</h2>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>ID</th>
            <th>Adhérent</th>
            <th>Livre</th>
            <th>Date réservation</th>
            <th>Statut</th>
            <th>Envoyer rappel</th>
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
                    <button class="btn btn-info btn-sm" disabled>Envoyer rappel</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="/reservations/liste" class="btn btn-secondary">Retour à la liste</a>
</div>
</body>
</html> 