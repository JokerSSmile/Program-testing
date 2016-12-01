#include "stdafx.h"
#include <string>
#include <iostream>
#include "CDetermineTriangle.h"

CDetermineTriangle::CDetermineTriangle(const std::string& a, const std::string& b, const std::string& c)
{
	try
	{
		m_a = boost::lexical_cast<double>(a);
		m_b = boost::lexical_cast<double>(b);
		m_c = boost::lexical_cast<double>(c);
	}
	catch (const boost::bad_lexical_cast&)
	{
		throw std::bad_cast();
	}

	if (m_a <= 0 || m_b <= 0 || m_c <= 0)
	{
		throw std::invalid_argument("Неверный аргумент.\nСтороны должны иметь длину больше 0");
	}
}

TriangleType CDetermineTriangle::DetermineTriangleType()
{
	if ((m_a >= m_b + m_c) || (m_b >= m_a + m_c) || (m_c >= m_a + m_b))
	{
		return NOT_TRIANGLE;
	}
	else if ((m_a == m_b) && (m_b == m_c) && (m_a == m_c))
	{
		return EQUILATERAL;
	}
	else if ((m_a == m_b) || (m_a == m_c) || (m_b == m_c))
	{
		return ISOSCELES;
	}
	else
	{
		return NORMAL;
	}
}

std::string CDetermineTriangle::GetResult()
{
	switch (DetermineTriangleType())
	{
	case NORMAL:
		return "Обычный";
		break;
	case ISOSCELES:
		return "Равнобедренный";
		break;
	case EQUILATERAL:
		return "Равносторонний";
		break;
	case NOT_TRIANGLE:
		return "Не треугольник";
		break;
	}
}
