package recettes;

public class Gateau
	{
		private Double quantite;
		private String ingredient;
		
		//constructeur vide
			public Gateau()
			{
				this(null,null);
			}
			
		//constructeur avec 2 paramètres
			public  Gateau(String departement, Double ingredient)
			{
				this.ingredient="";
				this.quantite=0.0;
			}
		
		//Setters et Getters
		public String getIngredient() {
			return ingredient;
		}
		public void setIngredient(String Ingredient) {
			this.ingredient = Ingredient;
		}
		public Double getQuantite() {
			return quantite;
		}
		public void setQuantite(Double quantite) {
			this.quantite = quantite;
		}
		public double getNum1() {
			// TODO Auto-generated method stub
			return 0;
		}
		
	

}	