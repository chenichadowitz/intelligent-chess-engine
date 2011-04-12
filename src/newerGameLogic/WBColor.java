package newerGameLogic;

public enum WBColor {
	White{
		public String toString(){return "w";}
		public WBColor next(){return Black;}
	},
	Black{
		public String toString(){return "b";}
		public WBColor next(){return White;}
	};
	public WBColor next(){return White;}
}
