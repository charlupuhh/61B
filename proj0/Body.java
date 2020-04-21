import java.lang.Math;

public class Body {

	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	public double g = 6.67 * Math.pow(10, -11);

	public Body(double xP, double yP, double xV, double yV, double m, String img){
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Body(Body b){
		xxPos = b.xxPos;
		yyPos = b.yyPos;
		xxVel = b.xxVel;
		yyVel = b.yyVel;
		mass = b.mass;
		imgFileName = b.imgFileName;
	}

	public double calcDistance(Body b){
		double xdis = Math.abs(this.xxPos - b.xxPos);
		double ydis = Math.abs(this.yyPos - b.yyPos);
		double distance = Math.sqrt(xdis * xdis + ydis * ydis);
		return distance;
	}

	public double calcForceExertedBy(Body b){
		return g * this.mass * b.mass / Math.pow(this.calcDistance(b), 2);
	}

	public double calcForceExertedByX(Body b){
		return this.calcForceExertedBy(b) * (b.xxPos - this.xxPos) / this.calcDistance(b);
	}

	public double calcForceExertedByY(Body b){
		return this.calcForceExertedBy(b) * (b.yyPos - this.yyPos) / this.calcDistance(b);
	}

	public double calcNetForceExertedByX(Body[] b){
		double netForceX = 0;
		for (int i = 0; i < b.length; i++){
			if (!this.equals(b[i])){
				netForceX += this.calcForceExertedByX(b[i]);
			}
		}
		return netForceX;
	}

	public double calcNetForceExertedByY(Body[] b){
		double netForceY = 0;
		for (int i = 0; i < b.length; i++){
			if (!this.equals(b[i])){
				netForceY += this.calcForceExertedByY(b[i]);
			}
		}
		return netForceY;
	}

	public void update(double t, double xForce, double yForce){
		double xAccel = xForce / this.mass;
		double yAccel = yForce / this.mass;
		this.xxVel += t * xAccel;
		this.yyVel += t * yAccel;
		this.xxPos += t * this.xxVel;
		this.yyPos += t * this.yyVel;
	}

	public void draw(){
		StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
	}

}