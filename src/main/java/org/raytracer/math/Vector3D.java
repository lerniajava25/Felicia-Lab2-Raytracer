package org.raytracer.math;

public record Vector3D(double x, double y, double z) {
    public Vector3D add(Vector3D other) {
        return new Vector3D(x + other.x, y + other.y, z + other.z);
    }

    public Vector3D subtract(Vector3D other) {
        return new Vector3D(x - other.x, y - other.y, z - other.z);
    }

    public Vector3D scale(double s) {
        return new Vector3D(x * s, y * s, z * s);
    }

    public double dot(Vector3D other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public double length() {
        return Math.sqrt(this.dot(this));
    }

    public Vector3D normalize() {
        double len = length();
        if (len == 0) {
            throw new IllegalArgumentException("Can't normalize when a vecor is zero");
        }
        return new Vector3D(x / len, y / len, z / len);
    }

    public Vector3D cross(Vector3D other) {
        double newX = y * other.z - z * other.y;
        double newY = z * other.x - x * other.z;
        double newZ = x * other.y - y * other.x;
        return new Vector3D(newX, newY, newZ);
    }
}
