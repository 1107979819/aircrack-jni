Array.prototype.each = function(fn)
{
	for (var i=0; i<this.length; i++)
		fn(this[i]);
}
