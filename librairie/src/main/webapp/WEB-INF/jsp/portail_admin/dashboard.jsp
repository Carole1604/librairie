<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Tableau de Bord Admin</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Tableau de Bord Administrateur</h2>
    <div class="row">
        <div class="col-md-4">
            <div class="card mb-3">
                <div class="card-body">
                    <h5 class="card-title">Nombre d'adhérents</h5>
                    <p class="card-text">${stats.nbAdherents}</p>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card mb-3">
                <div class="card-body">
                    <h5 class="card-title">Nombre de livres</h5>
                    <p class="card-text">${stats.nbLivres}</p>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card mb-3">
                <div class="card-body">
                    <h5 class="card-title">Nombre de prêts en cours</h5>
                    <p class="card-text">${stats.nbPretsEnCours}</p>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-4">
            <div class="card mb-3">
                <div class="card-body">
                    <h5 class="card-title">Nombre de réservations</h5>
                    <p class="card-text">${stats.nbReservations}</p>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card mb-3">
                <div class="card-body">
                    <h5 class="card-title">Nombre de pénalités actives</h5>
                    <p class="card-text">${stats.nbPenalitesActives}</p>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card mb-3">
                <div class="card-body">
                    <h5 class="card-title">Nombre de prolongements</h5>
                    <p class="card-text">${stats.nbProlongements}</p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html> 