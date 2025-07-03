<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Détail Pénalité</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Détail de la Pénalité</h2>
    <ul class="list-group">
        <li class="list-group-item"><strong>ID :</strong> ${penalite.id}</li>
        <li class="list-group-item"><strong>Adhérent :</strong> ${penalite.adherent.nom} ${penalite.adherent.prenom}</li>
        <li class="list-group-item"><strong>Prêt :</strong> ${penalite.pret.id}</li>
        <li class="list-group-item"><strong>Date début :</strong> ${penalite.dateDebut}</li>
        <li class="list-group-item"><strong>Date fin :</strong> ${penalite.dateFin}</li>
        <li class="list-group-item"><strong>Montant :</strong> ${penalite.montant}</li>
        <li class="list-group-item"><strong>Motif :</strong> ${penalite.motif}</li>
        <li class="list-group-item"><strong>Nombre de jours de retard :</strong> ${penalite.nombreJoursRetard}</li>
        <li class="list-group-item"><strong>Active :</strong> <c:choose><c:when test="${penalite.estActive}">Oui</c:when><c:otherwise>Non</c:otherwise></c:choose></li>
    </ul>
    <a href="/penalites/liste" class="btn btn-secondary mt-3">Retour à la liste</a>
</div>
</body>
</html> 