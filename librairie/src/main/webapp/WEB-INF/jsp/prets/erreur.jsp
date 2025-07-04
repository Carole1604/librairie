<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Erreur Prêt</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Erreur lors de la création du prêt</h2>
    <div class="alert alert-danger">
        <p>${erreur}</p>
    </div>
    <a href="/prets/creation" class="btn btn-primary">Retour au formulaire</a>
</div>
</body>
</html> 