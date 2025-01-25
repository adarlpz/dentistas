<?php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET');
header('Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With');

$host = "localhost"; 
$user = "root"; 
$password = ""; 
$dbname = "dentistas"; 


$conn = new mysqli($host, $user, $password, $dbname);


if ($conn->connect_error) {
    echo json_encode([
        "status" => "error",
        "message" => "Error de conexión a la base de datos: " . $conn->connect_error
    ]);
    exit();
}

if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    if (isset($_GET['id']) && !empty($_GET['id'])) {
        $id = $_GET['id'];

        $sql = "DELETE FROM dentistas WHERE id = ?";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("i", $id);

        if ($stmt->execute()) {
            if ($stmt->affected_rows > 0) {
                echo json_encode([
                    "status" => "success",
                    "message" => "Dentista eliminado correctamente"
                ]);
            } else {
                echo json_encode([
                    "status" => "error",
                    "message" => "No se encontró un dentista con el ID proporcionado"
                ]);
            }
        } else {
            echo json_encode([
                "status" => "error",
                "message" => "Error al ejecutar la consulta: " . $stmt->error
            ]);
        }

        $stmt->close();
    } else {
        echo json_encode([
            "status" => "error",
            "message" => "ID no proporcionado o vacío"
        ]);
    }
} else {
    echo json_encode([
        "status" => "error",
        "message" => "Método no permitido, usa GET"
    ]);
}

$conn->close();
?>
