package pojo;
public class dentista {
    public String nombrecompleto;
    public String licencia;
    public String fechanacimiento;
    public String telefono;
    public String email;
    public String direccion;
    public String calificacion;
    public String especialidad;
    public String horaapertura;
    public String horacierre;
    private boolean isChecked;
    public String getFechanacimiento() {
        return fechanacimiento;
    }
    public void setFechanacimiento(String fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }
    public String getNombrecompleto() {
        return nombrecompleto;
    }
    public void setNombrecompleto(String nombrecompleto) {
        this.nombrecompleto = nombrecompleto;
    }
    public String getLicencia() {
        return licencia;
    }
    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getCalificacion() {
        return calificacion;
    }
    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }
    public String getEspecialidad() {
        return especialidad;
    }
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    public String getHoraapertura() {
        return horaapertura;
    }
    public void setHoraapertura(String horaapertura) {
        this.horaapertura = horaapertura;
    }
    public String getHoracierre() {
        return horacierre;
    }
    public void setHoracierre(String horacierre) {
        this.horacierre = horacierre;
    }
    public boolean isChecked() {
        return isChecked;
    }
    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}