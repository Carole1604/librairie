<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Prolongements</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Liste des Demandes de Prolongement</h2>
    <a href="/prolongements/demande" class="btn btn-primary mb-3">Nouvelle demande</a>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>ID</th>
            <th>ID Prêt</th>
            <th>Nouvelle date de rendu</th>
            <th>Statut</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="prolongement" items="${prolongements}">
            <tr>
                <td>${prolongement.id}</td>
                <td>${prolongement.pret.id}</td>
                <td>${prolongement.nouvelleDateRendu}</td>
                <td>${prolongement.statut.nomStatut}</td>
                <td>
                    <a href="/prolongements/validation/${prolongement.id}" class="btn btn-success btn-sm">Valider/Refuser</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html> 