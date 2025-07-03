<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Paramétrage des Quotas d'Emprunt</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2>Configuration des Quotas d'Emprunt</h2>
    <form>
        <div class="mb-3">
            <label class="form-label">Étudiant</label>
            <input type="number" class="form-control" placeholder="Quota max" min="1">
        </div>
        <div class="mb-3">
            <label class="form-label">Professionnel</label>
            <input type="number" class="form-control" placeholder="Quota max" min="1">
        </div>
        <div class="mb-3">
            <label class="form-label">Anonyme</label>
            <input type="number" class="form-control" placeholder="Quota max" min="1">
        </div>
        <button type="submit" class="btn btn-success">Enregistrer</button>
        <a href="/" class="btn btn-secondary">Annuler</a>
    </form>
</div>
</body>
</html> 