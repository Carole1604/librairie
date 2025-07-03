<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ajouter un Jour Férié</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Ajouter un Jour Férié</h2>
    <form action="/joursferies/ajout" method="post">
        <div class="mb-3">
            <label for="nomJourFerie" class="form-label">Nom du jour férié</label>
            <input type="text" class="form-control" id="nomJourFerie" name="nomJourFerie" required>
        </div>
        <div class="mb-3">
            <label for="dateFerie" class="form-label">Date</label>
            <input type="date" class="form-control" id="dateFerie" name="dateFerie" required>
        </div>
        <button type="submit" class="btn btn-success">Ajouter</button>
        <a href="/joursferies/liste" class="btn btn-secondary">Annuler</a>
    </form>
</div>
</body>
</html> 