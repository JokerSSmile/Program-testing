#include "stdafx.h"
#include "CDetermineTriangle.h"
#include <iostream>
#include <locale.h>

int main(int argc, char* argv[])	
{
	setlocale(0, "RUS");

	if (argc != 4)
	{
		std::cout << "�������� �����.\n������ �����: triangle.exe <1 �������> <2 �������> <3 �������>" << std::endl;
		return 1;
	}
	try
	{
		CDetermineTriangle triangle = CDetermineTriangle(argv[1], argv[2], argv[3]);
		std::cout << triangle.GetResult() << std::endl;
	}
	catch (const std::bad_cast&)
	{
		std::cout << "�������� ��������.\n����������� ������ ���� �����." << std::endl;
		return 2;
	}
	catch (const std::exception& e)
	{
		std::cout << e.what() << std::endl;
		return 2;
	}

    return 0;
}