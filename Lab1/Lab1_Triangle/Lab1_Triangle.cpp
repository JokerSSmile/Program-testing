#include "stdafx.h"
#include "CDetermineTriangle.h"
#include <iostream>
#include <locale.h>

int main(int argc, char* argv[])	
{
	setlocale(0, "RUS");

	if (argc != 4)
	{
		//std::cout << "Invalid parameters.\n Input must be Lab1_Triangle <1 side> <2 side> <3 side>" << std::endl;
		std::cout << "Неверный вызов.\nФормат ввода: triangle.exe <1 сторона> <2 сторона> <3 сторона>" << std::endl;
		return 1;
	}
	try
	{
		CDetermineTriangle triangle = CDetermineTriangle(argv[1], argv[2], argv[3]);
		triangle.DetermineTriangleType();
		triangle.OutputResult();
	}
	catch (std::exception& e)
	{
		std::cout << e.what() << std::endl;
		return 2;
	}
	
    return 0;
}
