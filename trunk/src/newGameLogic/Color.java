package newGameLogic;

public enum Color {
	White{
		public String toString(){return "w";}
		public Color next(){return Black;}
	},
	Black{
		public String toString(){return "b";}
		public Color next(){return White;}
	};
	
	public Color next(){return White;}
}
