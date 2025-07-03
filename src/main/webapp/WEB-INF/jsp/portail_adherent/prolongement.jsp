<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Demande de Prolongement</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5" style="max-width:500px;">
    <h2>Demande de Prolongement</h2>
    <form action="/portail_adherent/prolongement" method="post">
        <div class="mb-3">
            <label for="idPret" class="form-label">ID du prêt</label>
            <input type="number" class="form-control" id="idPret" name="pret.id" required>
        </div>
        <div class="mb-3">
            <label for="nouvelleDateRendu" class="form-label">Nouvelle date de rendu</label>
            <input type="date" class="form-control" id="nouvelleDateRendu" name="nouvelleDateRendu" required>
        </div>
        <button type="submit" class="btn btn-success">Demander</button>
    </form>
</div>
</body>
</html> 