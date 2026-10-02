package vallegrande.edu.pe.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DuenoDAO {

    public List<Dueno> listar() throws SQLException {
        List<Dueno> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, telefono, direccion FROM duenos";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Dueno(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("direccion")
                ));
            }
        }
        return lista;
    }

    public boolean insertar(Dueno dueno) throws SQLException {
        String sql = "INSERT INTO duenos (nombre, telefono, direccion) VALUES (?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dueno.getNombre());
            ps.setString(2, dueno.getTelefono());
            ps.setString(3, dueno.getDireccion());

            return ps.executeUpdate() > 0;
        }
    }

}