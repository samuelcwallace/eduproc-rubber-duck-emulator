#!/usr/bin/perl -w
use strict;
my $usage= <<END_COMMENT
usage: perl maincodec.pl input.pwa output.java
help: perl maincodec.pl help
END_COMMENT
;
my $help = <<END_COMMENT

usage: perl maincodec.pl input.pwa output.java
Program to convert  program writing assistant files (.pwa) to Java files (.java)
This is just a parser, not a compiler. The resulting .java file can be compiled as any other would be,
For syntax in .pwa files, read parserspec.pwa
END_COMMENT
;
#print out help if requeseted
if (scalar @ARGV == 1 &&  $ARGV[0] eq "help"){
	print $help;
	exit;
}
if (scalar @ARGV != 2 ){
	# if improperly used, print out proper usage information and exit
	# perl does not store the program name in @ARGV	
	print $usage;
	exit;
}

open (INCODE, $ARGV[0]) or die "Cannot open or find file";
open (OUTCODE, ">$ARGV[1]") or die "Cannot write to file";
my $direction = "encode";
#stack currently has no effect, but can be used for debugging
my @stack; 
my $line;
my $function= "void";
my $block= "none";
my $classname = $ARGV[1];
$classname =~ s/.java//;

#imports: currently importing commonly used libraries
#edit this to change what gets imported
print OUTCODE "import java.util.Scanner;\n";
print OUTCODE "import java.util.ArrayList;\n";

print OUTCODE "class $classname {\n";
while ($line =<INCODE>){
	my $type = "NA";	
	my $varname = "not found";
	my $min= "not found";
	my $max= "not found";
	my $condition = "not found";
	my $value = "not found";
	my $outline = $line; #if nothing else found, just return the original line
	my $params = "";
	my $symbol = "";
	 			 
	if ($line =~ /^\s*for/){
		#for every integer i between 0 and 1
		($varname,  $min,  $max) = ($line =~ /^\s*for every integer (\w+) between (\d+) and (\d+)/) or die "Synax error on for loop!";
		$block = "for";
		$outline = "for (int $varname = $min; $varname < $max; $varname ++){\n";
		push @stack, $block; 
	}
	elsif ($line =~ /^\s*if/){
		#if i is less than 1
		($varname, $condition, $value) = ($line =~ /^\s*if (\w+) is (\w+\s*\w+) (\w+)/) or die "Syntax error on if statement!";
		$block = "if";
		$symbol = ct2s ($condition);
		$outline = "if ($varname $symbol $value){\n"; 
		push @stack, $block; 
	}

	elsif ($line =~ /^\s*otherwise, if/){

		$block = "elsif";
		push @stack, $block;
		($varname, $condition, $value) = ($line =~ /^\s*if (\w+) is ((\w+){2}) ("*\w+"*)/) or die "Syntax error on if statement!";
		$block = "if";
		$symbol = ct2s ($condition);
		$outline = "if ($varname $symbol $value){\n"; 
	}
	elsif ($line =~ /^\s*while/){
		#while i is less than 1
		($varname, $condition, $value) = ($line =~ /^\s*while (\w+) is ((\w+){2}) ("*\w+"*)/) or die "Syntax error on while loop!";
		$symbol = ct2s ($condition);
		$outline = "while ($varname $symbol $value){"; 
		$block = "while";
		push @stack, $block; 
	}
	elsif ($line =~ /^\s*otherwise/){
		#otherwise
		$outline = "else{";
		$block = "else";
		push @stack, $block; 
	}
	elsif ($line =~ /^\s*end/){
		$outline = "}\n";
		pop @stack;
	}
	elsif ($line =~ /^\s*function/){
		#function called foo that returns an integer
		#function called foo that returns a string
		#function called foo that returns a floating point number
		if (  ($varname, $value, $params) =($line =~ /^\s*function called (\w+) that returns (\w+) and takes (.+),$/)){
			$outline = "$value $varname ($params){";
		}
		else{
			($varname,  $value) = ($line =~ /^\s*function called (\w+) that returns (\w+)/)or die "Syntax error on function declaration!";
			$outline = "$value $varname (){\n"; 
		}
		print "$varname $value \n";
	}

	elsif ($line =~ /^\s*display/){
		#display "Hello world!"
		#display x
		
		($value) = ($outline =~ /display (.+)$/);	
		$outline = "System.out.println($value);";
	}
	elsif ($line =~ /calculate/){
		#set value of variable to value returned by a function 	
		#calculate the value of foo with function plusone using arguments 1
		#calculate the value of foo with function getheight
		if ( ($varname, $value, $params) = ($outline =~ /^\s*calculate the value of (\w+) with function (.+) using arguments (\w+ )+,/)){
			$outline = "$varname = $value ($params);";
		}
		else {
			($varname, $value) = ($outline =~ /^\s*calculate the value of (\w+) with function (.+)/) or die ("Syntax error on function call!");
			$outline = "$varname = $value();";
		}
	}
	elsif ($line =~ /^\s*call/){
		if ( ( $value, $params) = ($outline =~ /call (\w+) with arguments (.+),$/)){
			$outline = "$value ($params);";
		}
		else {
			($value) = ($outline =~ /call (\.+)$/) or die ("Syntax error on function call!");
			$outline = "$value();";
		}
	}

	elsif ($line =~ /^\s*set/){
		#set i to 5
		($varname, $value) = ($outline =~ /set (\w+) to (.+)$/) or die ("Syntax error on value assignment");
		$outline = "$varname = $value;";
	}
	#create a new object
	elsif ($line =~ /make/){
		#make a new Integer called i
		#make a new Dingus called ding 
		#creating variables initialized is currently not supported
		#if (($type, $varname, $params) = ($outline =~ /make a new (\w+) called (\w+) with input of (.+),$/) ){
		#	$outline = "$type $varname = new $type ($params);";
		#}
		#else{
			($type, $varname) = ($outline =~ /make a new (\w+) called (\w+)/) or die ("Syntax error on object creation!");
			$outline = "$type $varname;";

			#}
	}
	elsif ($outline =~ /main function/){
		$outline = "public static void main (String... args){";
	}

	
	print OUTCODE "$outline\n";
}
print OUTCODE "}";
sub ct2s{
	#simple function that converts words to comparators
	my $sign = $_[0];
	return ">" if ($sign eq "greater than");	
	return "<" if ($sign eq "less than");
	return "==" if ($sign eq "equal to");
	return ">=" if ($sign eq "greater than or equal to");
	return "<=" if ($sign eq "lesser than or equal to");
	return "?";
}
