<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Jours Fériés</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Liste des Jours Fériés</h2>
    <a href="/joursferies/ajout" class="btn btn-primary mb-3">Ajouter un jour férié</a>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Date</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="jour" items="${joursFeries}">
            <tr>
                <td>${jour.id}</td>
                <td>${jour.nomJourFerie}</td>
                <td>${jour.dateFerie}</td>
                <td>
                    <a href="/joursferies/modifier/${jour.id}" class="btn btn-warning btn-sm">Modifier</a>
                    <form action="/joursferies/supprimer" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="${jour.id}" />
                        <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Supprimer ce jour férié ?');">Supprimer</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html> 