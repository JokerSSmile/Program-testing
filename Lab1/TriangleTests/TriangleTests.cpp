#include "stdafx.h"
#include "../Lab1_Triangle/CDetermineTriangle.h"

struct NormalTrinagles
{
	CDetermineTriangle triangle1;
	CDetermineTriangle triangle2;
	CDetermineTriangle triangle3;
	CDetermineTriangle triangle4;
	CDetermineTriangle triangle5;
	NormalTrinagles() :
		triangle1("3", "6", "4"),
		triangle2("7.3", "5.5", "4"),
		triangle3("6.2", "4.5", "5.3"),
		triangle4("100", "70", "40"),
		triangle5("0.100025", "0.100015", "0.100020")
	{};
};

struct IsoscelesTrinagles
{
	CDetermineTriangle triangle1;
	CDetermineTriangle triangle2;
	CDetermineTriangle triangle3;
	CDetermineTriangle triangle4;
	CDetermineTriangle triangle5;
	IsoscelesTrinagles() :
		triangle1("3", "3", "4"),
		triangle2("7.3", "5.5", "7.3"),
		triangle3("5.123", "4.5", "5.123"),
		triangle4("1000.1", "1000.1", "40"),
		triangle5("0.100025", "0.100015", "0.100025")
	{};
};

struct EquilateralTrinagles
{
	CDetermineTriangle triangle1;
	CDetermineTriangle triangle2;
	CDetermineTriangle triangle3;
	CDetermineTriangle triangle4;
	CDetermineTriangle triangle5;
	EquilateralTrinagles() :
		triangle1("3", "3", "3"),
		triangle2("7.3", "7.3", "7.3"),
		triangle3("5.123", "5.123", "5.123"),
		triangle4("1000", "1000", "1000"),
		triangle5("0.100025", "0.100025", "0.100025")
	{};
};

struct NotTriangle
{
	CDetermineTriangle triangle1;
	CDetermineTriangle triangle2;
	CDetermineTriangle triangle3;
	CDetermineTriangle triangle4;
	CDetermineTriangle triangle5;
	NotTriangle() :
		triangle1("4", "100", "3"),
		triangle2("4", "2", "2"),
		triangle3("50.255", "25.250", "25.005"),
		triangle4("100", "70", "20"),
		triangle5("0.10025", "0.10015", "0.00009")
	{};
};

BOOST_AUTO_TEST_SUITE(TriangleTests)

BOOST_AUTO_TEST_SUITE(works_correct_with_given_data)

	BOOST_AUTO_TEST_CASE(throws_exception_if_incorrect_symbol_in_parameters)
	{
		BOOST_REQUIRE_THROW(CDetermineTriangle("f", "3", "4"), std::bad_cast);
	}

	BOOST_AUTO_TEST_CASE(throws_exception_if_number_less_than_zero_in_parameters)
	{
		BOOST_REQUIRE_THROW(CDetermineTriangle("-4", "3", "-4"), std::invalid_argument);
	}

	BOOST_AUTO_TEST_CASE(no_excreption_if_correct_data)
	{
		BOOST_REQUIRE_NO_THROW(CDetermineTriangle("4", "3", "4"));
	}

BOOST_AUTO_TEST_SUITE_END()

BOOST_AUTO_TEST_SUITE(correct_determines_triangle_type)

BOOST_FIXTURE_TEST_CASE(correct_returns_normal, NormalTrinagles)
{
	BOOST_CHECK_EQUAL(triangle1.DetermineTriangleType(), NORMAL);
	BOOST_CHECK_EQUAL(triangle2.DetermineTriangleType(), NORMAL);
	BOOST_CHECK_EQUAL(triangle3.DetermineTriangleType(), NORMAL);
	BOOST_CHECK_EQUAL(triangle4.DetermineTriangleType(), NORMAL);
	BOOST_CHECK_EQUAL(triangle5.DetermineTriangleType(), NORMAL);
}

BOOST_FIXTURE_TEST_CASE(correct_returns_isosceles, IsoscelesTrinagles)
{
	BOOST_CHECK_EQUAL(triangle1.DetermineTriangleType(), ISOSCELES);
	BOOST_CHECK_EQUAL(triangle2.DetermineTriangleType(), ISOSCELES);
	BOOST_CHECK_EQUAL(triangle3.DetermineTriangleType(), ISOSCELES);
	BOOST_CHECK_EQUAL(triangle4.DetermineTriangleType(), ISOSCELES);
	BOOST_CHECK_EQUAL(triangle5.DetermineTriangleType(), ISOSCELES);
}

BOOST_FIXTURE_TEST_CASE(correct_returns_equilateral, EquilateralTrinagles)
{
	BOOST_CHECK_EQUAL(triangle1.DetermineTriangleType(), EQUILATERAL);
	BOOST_CHECK_EQUAL(triangle2.DetermineTriangleType(), EQUILATERAL);
	BOOST_CHECK_EQUAL(triangle3.DetermineTriangleType(), EQUILATERAL);
	BOOST_CHECK_EQUAL(triangle4.DetermineTriangleType(), EQUILATERAL);
	BOOST_CHECK_EQUAL(triangle5.DetermineTriangleType(), EQUILATERAL);
}

BOOST_FIXTURE_TEST_CASE(correct_returns_if_not_triangle, NotTriangle)
{
	BOOST_CHECK_EQUAL(triangle1.DetermineTriangleType(), NOT_TRIANGLE);
	BOOST_CHECK_EQUAL(triangle2.DetermineTriangleType(), NOT_TRIANGLE);
	BOOST_CHECK_EQUAL(triangle3.DetermineTriangleType(), NOT_TRIANGLE);
	BOOST_CHECK_EQUAL(triangle4.DetermineTriangleType(), NOT_TRIANGLE);
	BOOST_CHECK_EQUAL(triangle5.DetermineTriangleType(), NOT_TRIANGLE);
}

BOOST_AUTO_TEST_SUITE_END()

BOOST_AUTO_TEST_SUITE(correct_outputs_triangle_type)

BOOST_FIXTURE_TEST_CASE(correct_output_if_normal, NormalTrinagles)
{
	BOOST_CHECK_EQUAL(triangle1.GetResult(), "Обычный");
}

BOOST_FIXTURE_TEST_CASE(correct_output_if_isosceles, IsoscelesTrinagles)
{
	BOOST_CHECK_EQUAL(triangle1.GetResult(), "Равнобедренный");
}

BOOST_FIXTURE_TEST_CASE(correct_output_if_equilateral, EquilateralTrinagles)
{
	BOOST_CHECK_EQUAL(triangle1.GetResult(), "Равносторонний");
}

BOOST_FIXTURE_TEST_CASE(correct_output_if_not_triangle, NotTriangle)
{
	BOOST_CHECK_EQUAL(triangle1.GetResult(), "Не треугольник");
}

BOOST_AUTO_TEST_SUITE_END()

BOOST_AUTO_TEST_SUITE_END()
