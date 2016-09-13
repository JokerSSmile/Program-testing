#include "stdafx.h"
#include <string>
#include <iostream>
#include "CDetermineTriangle.h"

CDetermineTriangle::CDetermineTriangle(std::string a, std::string b, std::string c)
{
	try
	{
		this->m_a = std::stod(a);
		this->m_b = std::stod(b);
		this->m_c = std::stod(c);
	}
	catch (const std::exception&)
	{
		//throw std::exception("Invalid arguments. Parameters must have type <double>");
		throw std::exception("Неверный аргумент.\n Аргументами должны быть числа.");
	}

	if (m_a <= 0 || m_b <= 0 || m_c <= 0)
	{
		//throw std::exception("Invalid arguments. Side must be more than 0");
		throw std::exception("Неверный аргумент.\nСтороны должны иметь длину больше 0");
	}
}

void CDetermineTriangle::DetermineTriangleType()
{
	if ((m_a >= m_b + m_c) || (m_b >= m_a + m_c) || (m_c >= m_a + m_b))
	{
		m_type = NOT_TRIANGLE;
	}
	else if ((m_a == m_b) && (m_b == m_c) && (m_a == m_c))
	{
		m_type = EQUILATERAL;
	}
	else if ((m_a == m_b) || (m_a == m_c) || (m_b == m_c))
	{
		m_type = ISOSCELES;
	}
	else
	{
		m_type = NORMAL;
	}
}

void CDetermineTriangle::OutputResult()
{
	switch (m_type)
	{
	case NORMAL:
		std::cout << "Обычный" << std::endl;
		break;
	case ISOSCELES:
		std::cout << "Равнобедренный" << std::endl;
		break;
	case EQUILATERAL:
		std::cout << "Равносторонний" << std::endl;
		break;
	case NOT_TRIANGLE:
		std::cout << "Не треугольник" << std::endl;
		break;
	default:
		break;
	}
}
