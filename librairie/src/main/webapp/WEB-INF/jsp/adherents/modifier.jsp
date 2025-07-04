<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Modifier un Adhérent</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Modifier un Adhérent</h2>
    <form action="/adherents/modifier" method="post">
        <input type="hidden" name="id" value="${adherent.id}" />
        <div class="mb-3">
            <label for="nom" class="form-label">Nom</label>
            <input type="text" class="form-control" id="nom" name="nom" value="${adherent.nom}" required>
        </div>
        <div class="mb-3">
            <label for="prenom" class="form-label">Prénom</label>
            <input type="text" class="form-control" id="prenom" name="prenom" value="${adherent.prenom}" required>
        </div>
        <div class="mb-3">
            <label for="login" class="form-label">Login</label>
            <input type="text" class="form-control" id="login" name="login" value="${adherent.login}" required>
        </div>
        <div class="mb-3">
            <label for="motDePasse" class="form-label">Mot de passe</label>
            <input type="password" class="form-control" id="motDePasse" name="motDePasse" value="${adherent.motDePasse}" required>
        </div>
        <div class="mb-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" class="form-control" id="email" name="email" value="${adherent.email}">
        </div>
        <div class="mb-3">
            <label for="telephone" class="form-label">Téléphone</label>
            <input type="text" class="form-control" id="telephone" name="telephone" value="${adherent.telephone}">
        </div>
        <div class="mb-3">
            <label for="adresse" class="form-label">Adresse</label>
            <input type="text" class="form-control" id="adresse" name="adresse" value="${adherent.adresse}">
        </div>
        <div class="mb-3">
            <label for="dateNaissance" class="form-label">Date de naissance</label>
            <input type="date" class="form-control" id="dateNaissance" name="dateNaissance" value="${adherent.dateNaissance}">
        </div>
        <div class="mb-3">
            <label class="form-label">Type d'adhérent</label><br>
            <input type="radio" id="etudiant" name="type" value="etudiant" ${adherent.estEtudiant ? 'checked' : ''}>
            <label for="etudiant">Étudiant</label>
            <input type="radio" id="professionnel" name="type" value="professionnel" ${adherent.estProfessionnel ? 'checked' : ''}>
            <label for="professionnel">Professionnel</label>
            <input type="radio" id="anonyme" name="type" value="anonyme" ${adherent.estAnonyme ? 'checked' : ''}>
            <label for="anonyme">Anonyme</label>
        </div>
        <div class="mb-3">
            <label for="quotaMax" class="form-label">Quota maximum d'emprunts</label>
            <input type="number" class="form-control" id="quotaMax" name="quotaMax" min="1" value="${adherent.quotaMax}">
        </div>
        <button type="submit" class="btn btn-success">Enregistrer</button>
        <a href="/adherents/liste" class="btn btn-secondary">Annuler</a>
    </form>
</div>
</body>
</html> 