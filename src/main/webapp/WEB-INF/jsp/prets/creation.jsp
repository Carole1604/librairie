<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Création d'un Prêt</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Créer un Prêt</h2>
    <form action="/prets/creation" method="post">
        <div class="mb-3">
            <label for="adherent" class="form-label">Adhérent</label>
            <select class="form-control" id="adherent" name="adherent.id" required>
                <c:forEach var="adherent" items="${adherents}">
                    <option value="${adherent.id}">${adherent.nom} ${adherent.prenom}</option>
                </c:forEach>
            </select>
        </div>
        <div class="mb-3">
            <label for="exemplaire" class="form-label">Exemplaire</label>
            <select class="form-control" id="exemplaire" name="exemplaire.id" required>
                <c:forEach var="exemplaire" items="${exemplaires}">
                    <option value="${exemplaire.id}">${exemplaire.nomExemplaire} (${exemplaire.livre.titre})</option>
                </c:forEach>
            </select>
        </div>
        <div class="mb-3">
            <label for="typePret" class="form-label">Type de prêt</label>
            <select class="form-control" id="typePret" name="typePret">
                <option value="domicile">Domicile</option>
                <option value="sur_lieu">Sur place</option>
            </select>
        </div>
        <div class="mb-3">
            <label for="dateFinPrevue" class="form-label">Date de fin prévue</label>
            <input type="date" class="form-control" id="dateFinPrevue" name="dateFinPrevue" required>
        </div>
        <button type="submit" class="btn btn-success">Créer</button>
        <a href="/prets/liste" class="btn btn-secondary">Annuler</a>
    </form>
</div>
</body>
</html> 