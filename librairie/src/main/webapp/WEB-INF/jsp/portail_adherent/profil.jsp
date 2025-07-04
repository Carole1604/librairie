<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Profil Adhérent</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Profil de l'Adhérent</h2>
    <ul class="list-group">
        <li class="list-group-item"><strong>Nom :</strong> ${adherent.nom}</li>
        <li class="list-group-item"><strong>Prénom :</strong> ${adherent.prenom}</li>
        <li class="list-group-item"><strong>Login :</strong> ${adherent.login}</li>
        <li class="list-group-item"><strong>Email :</strong> ${adherent.email}</li>
        <li class="list-group-item"><strong>Téléphone :</strong> ${adherent.telephone}</li>
        <li class="list-group-item"><strong>Adresse :</strong> ${adherent.adresse}</li>
        <li class="list-group-item"><strong>Date de naissance :</strong> ${adherent.dateNaissance}</li>
        <li class="list-group-item"><strong>Type :</strong> <c:choose><c:when test="${adherent.estEtudiant}">Étudiant</c:when><c:when test="${adherent.estProfessionnel}">Professionnel</c:when><c:otherwise>Anonyme</c:otherwise></c:choose></li>
        <li class="list-group-item"><strong>Quota max :</strong> ${adherent.quotaMax}</li>
        <li class="list-group-item"><strong>Quota actuel :</strong> ${adherent.quotaActuel}</li>
    </ul>
</div>
</body>
</html> 